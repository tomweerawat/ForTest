package com.example.base.recyclerview

import android.content.ClipData
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.base.R
import com.example.base.recyclerview.base.BaseItems
import com.example.base.recyclerview.base.BaseViewHolder
import com.miguelcatalan.materialsearchview.MaterialSearchView


import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.listeners.OnClickListener
import com.mikepenz.fastadapter.listeners.OnLongClickListener
import com.mikepenz.fastadapter_extensions.items.ProgressItem
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener

/**
 * The type Base list.
 *
 * @param <TItem> the type parameter
</TItem> */
abstract class BaseList<TItem> : Fragment(), OnClickListener<TItem>, OnLongClickListener<TItem>
        where TItem : BaseItems<*, *, *> {

    private lateinit var mRootView: View
    private lateinit var emptyView: View
    private lateinit var emptyAction: View
    private lateinit var emptySearch: View

    //save our FastAdapter
    private lateinit var fastItemAdapter: FastItemAdapter<TItem>
    private lateinit var footerAdapter: ItemAdapter<TItem>

    /**
     * The M swipe refresh layout.
     */
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    /**
     * The Endless recycler on scroll listener.
     */
    //endless scroll
    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener

    /**
     * The Is endless.
     */
    private var isEndless = false

    private lateinit var header: TextView
    private lateinit var detail: TextView
    private lateinit var imageEmptyView: ImageView

    private lateinit var progressBar: View

    /**
     * The Recycler view.
     */
    private lateinit var recyclerView: RecyclerView

    /**
     * The Base list action.
     */
    private var baseListAction = BaseListAction.None
    private var lastClickMillis: Long = 0

    companion object {

        private val THRESHOLD_MILLIS = 1000L
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        mRootView = inflater.inflate(R.layout.fragment_base_list, container, false)

        emptyView = mRootView.findViewById(R.id.first_document)
        emptyAction = mRootView.findViewById(R.id.click_first_document)
        emptySearch = mRootView.findViewById(R.id.search_empty)

        header = mRootView.findViewById(R.id.first_create_herder_text)
        detail = mRootView.findViewById(R.id.first_create_detail_text)
        imageEmptyView = mRootView.findViewById(R.id.imageView3)

        progressBar = mRootView.findViewById(R.id.emptyview_progress)


        emptyView(header, detail, imageEmptyView)


        onLoad(0, 20, BaseListAction.None)

        //create our FastAdapter which will manage everything
        fastItemAdapter = FastItemAdapter()
        fastItemAdapter.withSelectable(true)

        fastItemAdapter.withOnClickListener { v, adapter, item, position ->
            val now = SystemClock.elapsedRealtime()
            if (now - lastClickMillis > THRESHOLD_MILLIS) {
                this@BaseList.onClick(v, adapter, item, position)
            }
            lastClickMillis = now
            false
        }
        fastItemAdapter.withOnLongClickListener(this)

        //create our FooterAdapter which will manage the progress items
        footerAdapter = ItemAdapter()
        fastItemAdapter.addAdapter(1, footerAdapter)


        mSwipeRefreshLayout = mRootView.findViewById(R.id.swipe_refresh)
        mSwipeRefreshLayout.setOnRefreshListener {

            //For not support endless action must be = Pull to refresh and non
            baseListAction = BaseListAction.PullToRefresh
            resetItem()
            baseListAction = BaseListAction.None
        }

        //get our recyclerView and do basic setup
        recyclerView = mRootView.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = fastItemAdapter
        endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener(footerAdapter) {
            override fun onLoadMore(currentPage: Int) {
                isEndless = true
                footerAdapter.clear()
                footerAdapter.add(ProgressItem2().withEnabled(false) as TItem)

                //
                baseListAction = if (baseListAction == BaseListAction.None) BaseListAction.EndLess else baseListAction
                onLoad(if (currentPage == 0) 0 else currentPage * 20, 20, baseListAction)
            }

        }
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener)

        emptyAction.setOnClickListener { firstItem() }


        //restore selections (this has to be done after the items were added
        if (savedInstanceState != null) {
            fastItemAdapter.withSavedInstanceState(savedInstanceState)
        }

        return mRootView
    }

    /**
     * New item.
     *
     * @param item the item
     */
    protected fun newItem(item: TItem) {
        fastItemAdapter.add(0, item)
        this.emptyView.visibility = View.GONE
        this.progressBar.visibility = View.GONE

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        if (firstVisiblePosition == 0)
            fastItemAdapter.notifyDataSetChanged()
    }

    /**
     * Add.
     *
     * @param items the items
     */
    protected fun add(items: List<TItem>) {
        progressBar.visibility = View.GONE

        if (isEndless) {
            footerAdapter.clear()
        }

        if (items.size < 20)
            endlessRecyclerOnScrollListener.disable()
        else
            endlessRecyclerOnScrollListener.enable()


        if (items.isNotEmpty()) {
            if (isEndless) {
                fastItemAdapter.add(fastItemAdapter.adapterItemCount, items)

            } else {
                footerAdapter.clear()
                fastItemAdapter.add(items)
            }
        }

        if (fastItemAdapter.adapterItemCount == 0) {
            when (baseListAction) {
                BaseList.BaseListAction.Search -> this.emptySearch.visibility = View.VISIBLE
                BaseList.BaseListAction.None -> {
                    this.emptySearch.visibility = View.GONE
                    this.emptyView.visibility = View.VISIBLE
                }
                else -> this.emptyView.visibility = View.VISIBLE
            }

        } else {
            this.emptyView.visibility = View.GONE
            this.emptySearch.visibility = View.GONE
        }
    }

    /**
     * Update.
     *
     * @param position the position
     * @param item     the item
     */
    protected fun update(position: Int, item: TItem) {
        fastItemAdapter.set(position, item)
    }

    /**
     * Remove.
     *
     * @param position the position
     */
    protected fun remove(position: Int) {
        fastItemAdapter.remove(position)
        if (fastItemAdapter.adapterItemCount == 0) {
            when (baseListAction) {
                BaseList.BaseListAction.Search -> this.emptySearch.visibility = View.VISIBLE
                else -> this.emptyView.visibility = View.VISIBLE
            }

        }
    }

    /**
     * Failure.
     */
    protected fun failure() {
        this.progressBar.visibility = View.GONE
        this.mSwipeRefreshLayout.isRefreshing = false

        if (fastItemAdapter.adapterItemCount == 0) {
            when (baseListAction) {
                BaseList.BaseListAction.Search -> this.emptySearch.visibility = View.VISIBLE
                else -> this.emptyView.visibility = View.VISIBLE
            }

        }
    }

    private fun resetItem() {
        mSwipeRefreshLayout.isRefreshing = false

        endlessRecyclerOnScrollListener.resetPageCount(0)
        footerAdapter.clear()
        fastItemAdapter.clear()
    }


    /**
     * On load.
     *
     * @param offset the offset
     * @param limit  the limit
     * @param action the action
     */
    protected abstract fun onLoad(offset: Int, limit: Int, action: BaseListAction)

    /**
     * Search.
     *
     * @param query the query
     */
    protected abstract fun search(query: String)

    /**
     * First item.
     */
    protected abstract fun firstItem()

    /**
     * Empty view.
     *
     * @param header    the header
     * @param detail    the detail
     * @param imageView the image view
     */
    protected abstract fun emptyView(header: TextView, detail: TextView, imageView: ImageView)


    override fun onLongClick(v: View, adapter: IAdapter<TItem>, item: TItem, position: Int): Boolean {
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {

        //add the values which need to be saved from the adapter to the bundle
        val outState = fastItemAdapter.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }


//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        inflater!!.inflate(R.menu.search_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//
//        val searchView = activity?.findViewById<View>(R.id.search_view) as? MaterialSearchView
//        val item = menu!!.findItem(R.id.action_search)
//        item.setOnMenuItemClickListener { false }
//
//        searchView?.setMenuItem(item)
//        searchView?.setSuggestions(arrayOfNulls(1))
//        searchView?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                this@MainActivity.search(query)
//
//                progressBar.visibility = View.VISIBLE
//                searchView.clearFocus()
//
//                resetItem()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//
//                return false
//            }
//        })
//
//        searchView?.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
//            override fun onSearchViewShown() {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    activity!!.window.statusBarColor = ContextCompat.getColor(activity!!.baseContext, R.color.primary)
//                }
//                baseListAction = BaseListAction.Search
//                searchView.setSuggestions(arrayOfNulls(0))
//                searchView.dismissSuggestions()
//
//                this@MainActivity.mSwipeRefreshLayout.isRefreshing = false
//                this@MainActivity.mSwipeRefreshLayout.isEnabled = false
//            }
//
//            override fun onSearchViewClosed() {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    activity!!.window.statusBarColor = ContextCompat.getColor(activity!!.baseContext, R.color.primary)
//                }
//                baseListAction = BaseListAction.None
//                this@MainActivity.progressBar.visibility = View.VISIBLE
//                this@MainActivity.mSwipeRefreshLayout.isEnabled = true
//
//                resetItem()
//
//            }
//        })
//
//    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        val id = item!!.itemId
//
//        if (id == R.id.action_create) {
//            this@MainActivity.firstItem()
//            return true
//        }
//
//        return false
//    }

    /**
     * The enum Base list action.
     */
    enum class BaseListAction {
        /**
         * None base list action.
         */
        None,
        /**
         * End less base list action.
         */
        EndLess,
        /**
         * Pull to refresh base list action.
         */
        PullToRefresh,
        /**
         * Search base list action.
         */
        Search
    }
}

private class ProgressItem2() : BaseItems<String, ProgressItem2, ProgressItem2.ViewHolder>("") {

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    override fun getLayoutRes(): Int {
        return com.mikepenz.library_extensions.R.layout.progress_item
    }

    override fun getType(): Int {
        return com.mikepenz.library_extensions.R.id.progress_item_id
    }

    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)
        if (isEnabled) {
            holder.itemView.setBackgroundResource(FastAdapterUIUtils.getSelectableBackground(holder.itemView.context))
        }
    }

    class ViewHolder(itemView: View) : BaseViewHolder<ProgressItem2>(itemView) {
        protected var progressBar: ProgressBar = itemView.findViewById(com.mikepenz.library_extensions.R.id.progress_bar)

        override fun unbindView(item: ProgressItem2) {

        }

        override fun bindView(item: ProgressItem2, payloads: MutableList<Any>) {

        }

    }
}

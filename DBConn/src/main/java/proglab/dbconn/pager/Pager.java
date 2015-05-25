package proglab.dbconn.pager;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.PagedList;


public final class Pager<T> {

	private final int pageSize;
	private final Class<T> clazz;
	
	private int page;
	private PagedList<T> pagedList;
	
	public Pager(final Class<T> clazz, final int pageSize) {
		this.clazz = clazz;
		this.page = 0;
		this.pageSize = pageSize;
		this.pagedList = Ebean.find(clazz).findPagedList(0, this.pageSize);
	}
	
	public boolean hasNext() {
		return this.pagedList.hasNext();
	}
	
	public List<T> next() {
		if (this.page > 0) {
			this.pagedList = Ebean.find(clazz).findPagedList(this.page, this.pageSize);
		}
		this.page++;
		return this.pagedList.getList();
	}

}

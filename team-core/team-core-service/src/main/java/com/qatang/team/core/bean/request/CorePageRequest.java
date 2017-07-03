package com.qatang.team.core.bean.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * author: sunshow.
 */
public class CorePageRequest extends PageRequest {

    private boolean withoutCountQuery = false;

    /**
     * Creates a new {@link PageRequest}. Pages are zero indexed, thus providing 0 for {@code page} will return the first
     * page.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     */
    public CorePageRequest(int page, int size) {
        super(page, size);
    }

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @param direction the direction of the {@link Sort} to be specified, can be {@literal null}.
     * @param properties the properties to sort by, must not be {@literal null} or empty.
     */
    public CorePageRequest(int page, int size, Sort.Direction direction, String... properties) {
        super(page, size, direction, properties);
    }

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @param sort can be {@literal null}.
     */
    public CorePageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    public boolean isWithoutCountQuery() {
        return withoutCountQuery;
    }

    public void setWithoutCountQuery(boolean withoutCountQuery) {
        this.withoutCountQuery = withoutCountQuery;
    }
}

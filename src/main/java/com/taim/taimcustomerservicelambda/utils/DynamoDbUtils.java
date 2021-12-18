package com.taim.taimcustomerservicelambda.utils;

import com.taim.taimcustomerservicelambda.models.dynamodb.Customer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DynamoDbUtils {

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * returns a sub list of given query result list from dynamoDB base on given pageNumber and pageSize.
     *
     * @param queryResult given query result list from dynamoDB.
     * @param pageNumber page number for pagination.
     * @param pageSize page size for pagination.
     * @return paginated result
     */
    public static <T> List<T> paginateResult(List<T> queryResult, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        int limit = pageSize;
        if (pageSize == 0) {
            limit = DEFAULT_PAGE_SIZE;
        }

        Iterator<T> resultIterator = queryResult.iterator();
        List<T> result = new ArrayList<>();

        // This method was implemented with while loop instead of list.subList() due to performance considerations.
        // When implementing with list.subList(), logic will need to call list.size() to make sure index is not
        // outside of list.size(). However, because dynamo db client makes multiple paginated api calls to dynamo db to
        // retrieve full requested data, a call to list.size() will perform a full table scan of the table and
        // introduce unnecessary negative performance impact.
        int i = 0;
        while (resultIterator.hasNext() && i < offset + limit) {
            T item = resultIterator.next();
            if (i >= offset) {
                result.add(item);
            }
            i++;
        }
        return result;
    }
}

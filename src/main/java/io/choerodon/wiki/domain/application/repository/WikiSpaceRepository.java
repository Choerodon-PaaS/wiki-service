package io.choerodon.wiki.domain.application.repository;

import java.util.List;

import io.choerodon.wiki.domain.application.entity.WikiSpaceE;

/**
 * Created by Zenger on 2018/7/2.
 */
public interface WikiSpaceRepository {

    List<WikiSpaceE> getWikiSpaceList(Long resourceId,String resourceType);

    void insert(WikiSpaceE wikiSpaceE);
}

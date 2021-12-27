package com.idus.hw.ui.web.user;

import com.idus.hw.common.collection.CollectionConverter;
import com.idus.hw.common.pagination.PaginationResult;
import com.idus.hw.common.web.dto.BaseResponse;
import com.idus.hw.core.order.domain.application.LatestOrderOfUsersQueryService;
import com.idus.hw.core.order.domain.entity.Order;
import com.idus.hw.core.user.application.user.UserSearchPaginationQueryService;
import com.idus.hw.core.user.domain.user.entity.User;
import com.idus.hw.ui.web.user.dto.SearchUsersRequest;
import com.idus.hw.ui.web.user.dto.SearchUsersResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/users/search")
@RequiredArgsConstructor
public class UserSearchController {
    private final UserSearchPaginationQueryService userSearchPaginationQueryService;
    private final LatestOrderOfUsersQueryService latestOrderOfUsersQueryService;

    @ApiOperation("회원 검색 API")
    @GetMapping
    public ResponseEntity<BaseResponse<SearchUsersResponse>> searchUser(
            ZoneId zoneId,
            SearchUsersRequest request
    ) {
        var searchedUsersResult = this.getSearchedUsersResult(request);
        var latestOrderOfUsers = this.getLatestOrderOrSearchedUsers(searchedUsersResult);
        return ResponseEntity.ok(
                BaseResponse.success(
                        SearchUsersResponse.of(searchedUsersResult, latestOrderOfUsers, zoneId)
                )
        );
    }

    private PaginationResult<User> getSearchedUsersResult(SearchUsersRequest request) {
        if (request.hasKeyword()) {
            return this.userSearchPaginationQueryService.searchUsers(
                    request.getName(),
                    request.getEmail(),
                    request.getPage(),
                    request.getPageSize()
            );
        }

        return this.userSearchPaginationQueryService.searchUsers(
                request.getPage(),
                request.getPageSize()
        );
    }

    private List<Order> getLatestOrderOrSearchedUsers(PaginationResult<User> searchedUsersResult) {
        var searchedUserIds = CollectionConverter.convertAndToList(searchedUsersResult.getContents(), User::getId);
        return this.latestOrderOfUsersQueryService.getLatestOrderOfUsers(searchedUserIds);
    }
}

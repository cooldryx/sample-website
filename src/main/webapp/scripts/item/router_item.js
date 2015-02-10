'use strict';

tianwendongApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/items', {
                    templateUrl: 'views/items.html',
                    controller: 'ItemController',
                    resolve: {
                        resolvedService: ['ItemService', 'Pagination', function (ItemService, Pagination) {
                            Pagination.setService(ItemService);
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                });
        });

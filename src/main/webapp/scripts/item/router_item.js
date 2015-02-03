'use strict';

tianwendongApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/items', {
                    templateUrl: 'views/items.html',
                    controller: 'ItemController',
                    resolve: {
                        resolvedItem: ['ItemService', function (ItemService) {
                            return ItemService.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.admin]
                    }
                });
        });

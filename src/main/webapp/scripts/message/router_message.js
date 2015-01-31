'use strict';

tianwendongApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/messages', {
                    templateUrl: 'views/messages.html',
                    controller: 'MessageController',
                    resolve: {
                        resolvedMessage: ['Message', function (Message) {
                            return Message.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.admin]
                    }
                });
        });

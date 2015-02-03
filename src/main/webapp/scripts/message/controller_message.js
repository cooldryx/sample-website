'use strict';

tianwendongApp.controller('MessageController', function ($scope, resolvedMessage, Message) {

        $scope.messages = resolvedMessage;

        //AngularJS does not support resolve in directive option at this time point, so I moved create to a new controller.

        $scope.delete = function (id) {
            Message.delete({id: id},
                function () {
                    $scope.messages = Message.query();
                });
        };
    });

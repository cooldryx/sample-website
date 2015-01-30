'use strict';

tianwendongApp.controller('MessageController', function ($scope, resolvedMessage, Message) {

        $scope.messages = resolvedMessage;

        $scope.create = function () {
            Message.save($scope.message,
                function () {
                    $scope.messages = Message.query();
                    $('#saveMessageModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.message = Message.get({id: id});
            $('#saveMessageModal').modal('show');
        };

        $scope.delete = function (id) {
            Message.delete({id: id},
                function () {
                    $scope.messages = Message.query();
                });
        };

        $scope.clear = function () {
            $scope.message = {email: null, nickname: null, content: null, created_time: null, ipv4: null, user_agent: null, id: null};
        };
    });

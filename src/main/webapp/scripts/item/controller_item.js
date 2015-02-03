'use strict';

tianwendongApp.controller('ItemController', function ($scope, resolvedItem, ItemService) {

        $scope.items = resolvedItem;

        $scope.create = function () {
            ItemService.save($scope.item,
                function () {
                    $scope.items = ItemService.query();
                    $('#saveItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.item = ItemService.get({id: id});
            $('#saveItemModal').modal('show');
        };

        $scope.delete = function (id) {
            ItemService.delete({id: id},
                function () {
                    $scope.items = ItemService.query();
                });
        };

        $scope.clear = function () {
            $scope.item = { name: null, star: null, description: null, id: null, path: null };
        };

    });

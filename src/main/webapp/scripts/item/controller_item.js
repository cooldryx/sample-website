'use strict';

tianwendongApp.controller('ItemController', function ($scope, resolvedService, ItemService, Pagination) {

        $scope.refresh = function () {
            Pagination.getResource().then(function (response) {
                Pagination.isFirst = response.first;
                Pagination.isLast = response.last;
                Pagination.totalPages = response.totalPages;
                Pagination.currentPage = parseInt(response.number);
                $scope.items = response.content;
            });
        };

        var currentPage = Pagination.currentPage;
        $scope.$watch('currentPage', function () {
            $scope.refresh();
        });

        $scope.create = function () {
            ItemService.save($scope.item,
                function () {
                    $scope.refresh();
                    $('#saveItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.item = ItemService.get({ id: id });
            $('#saveItemModal').modal('show');
        };

        $scope.delete = function (id) {
            ItemService.delete({ id: id },
                function () {
                    $scope.refresh();
                    $scope.items = ItemService.query();
                });
        };

        $scope.clear = function () {
            $scope.item = { name: null, star: null, description: null, id: null, path: null };
        };
    });

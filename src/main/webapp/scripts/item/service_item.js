'use strict';

tianwendongApp.factory('ItemService', function ($resource) {
        return $resource('app/rest/items/:id', {}, {
            'get': { method: 'GET' },
            'query': { method: 'GET', isArray: true }
        })
    });

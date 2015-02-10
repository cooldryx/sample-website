'use strict';

tianwendongApp.factory('ItemService', function ($http, $resource) {
        return $resource('app/rest/items/:id', {}, {
                'get': { method: 'GET' },
                'query': { method: 'GET', params: { page: '@page' } , isArray: false }
            });
    });

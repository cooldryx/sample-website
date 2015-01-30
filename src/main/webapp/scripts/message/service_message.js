'use strict';

tianwendongApp.factory('Message', function ($resource) {
        return $resource('app/rest/messages/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });

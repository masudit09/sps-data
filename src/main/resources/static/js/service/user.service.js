'use strict';

angular.module('app')
    .factory('User', function ($http, $resource) {
        return $resource('api/user/', {}, {
            'get'  : { method: 'GET', params: {}, format: 'json',
                transformResponse: [function (data, headersGetter) {
                console.log(data);
                    return data;
                }].concat($http.defaults.transformResponse)}
        });
    });



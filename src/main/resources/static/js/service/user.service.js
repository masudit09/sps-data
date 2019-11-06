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
    })
    .factory('UserChangePassword', function ($http, $resource) {
    return $resource('api/user/change-password', {}, {
        'update'  : { method: 'GET',headers: {'Content-Type': 'application/json'}, params: {}, format: 'json',
            transformResponse: [function (data, headersGetter) {
                return data;
            }].concat($http.defaults.transformResponse)}
    });
});



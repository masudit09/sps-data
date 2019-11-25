'use strict';

angular.module('app')
    .factory('Content', function ($http, $resource) {
        return $resource('api/content/:id', {}, {
            'get': {
                method: 'GET', headers: {'Content-Type': 'application/json'}, params: {}, format: 'json',
                transformResponse: [function (data, headersGetter) {
                    console.log(data);
                    return data;
                }].concat($http.defaults.transformResponse)
            },
            'save': {
                method: 'POST', headers: {'Content-Type': 'application/json'}, params: {}, format: 'json', isArray:true,
                transformResponse: [function (data, headersGetter) {
                    console.log(data);
                    return data;
                }].concat($http.defaults.transformResponse)
            },
            'update': {
                method: 'PUT', params: {}, format: 'json',
                transformResponse: [function (data, headersGetter) {
                    console.log(data);
                    return data;
                }].concat($http.defaults.transformResponse)
            },
            'delete': {
                method: 'DELETE', params: {}, format: 'json',
                transformResponse: [function (data, headersGetter) {
                    console.log(data);
                    return data;
                }].concat($http.defaults.transformResponse)
            }
        });
    }).factory('AllContent', function ($http, $resource) {
    return $resource('api/content/find-all', {}, {
        'get': {
            method: 'GET', headers: {'Content-Type': 'application/json'}, params: {}, format: 'json',
            transformResponse: [function (data, headersGetter) {
                console.log(data);
                return data;
            }].concat($http.defaults.transformResponse)
        }
    })
})
;



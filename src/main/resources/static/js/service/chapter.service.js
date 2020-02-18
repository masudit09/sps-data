'use strict';

angular.module('app')
    .factory('Chapter', function ($http, $resource) {
        return $resource('./api/chapter/:id', {}, {
            'get': {
                method: 'GET', headers: {'Content-Type': 'application/json'}, params: {}, format: 'json',
                transformResponse: [function (data, headersGetter) {
                    console.log(data);
                    return data;
                }].concat($http.defaults.transformResponse)
            },
            'save': {
                method: 'POST', headers: {'Content-Type': 'application/json'}, params: {}, format: 'json',
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
    }).factory('AllChapter', function ($http, $resource) {
    return $resource('./api/chapter/find-all', {}, {
        'get': {
            method: 'GET', headers: {'Content-Type': 'application/json'}, params: {}, format: 'json', isArray:true,
            transformResponse: [function (data, headersGetter) {
                console.log(data);
                return data;
            }].concat($http.defaults.transformResponse)
        }
    })
})
;



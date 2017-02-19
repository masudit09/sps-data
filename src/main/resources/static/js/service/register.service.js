'use strict';

angular.module('app')
    .factory('Register', function ($http, $resource) {
        return $resource('api/user/register', {}, {
            'save'  : { method: 'POST', params: {}, format: 'json',
                transformResponse: [function (data, headersGetter) {
                console.log(data);
                    return {id:data};
                }].concat($http.defaults.transformResponse)}
        });
    });



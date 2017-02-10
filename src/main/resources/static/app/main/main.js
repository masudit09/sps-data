'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: '/app/main/main.html',
                        controller: 'MainController'
                    }
                }
            })

         });

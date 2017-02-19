'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('register-user', {
                parent: 'account',
                url: '/register',
                data: {
                    authorities: [],
                    pageTitle: 'register.title'
                },
                views: {
                    'content@': {
                        templateUrl: '/views/register.html',
                        controller: 'RegisterController'
                    }
                }/*,
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('register');
                        return $translate.refresh();
                    }]
                }*/
            });
    });

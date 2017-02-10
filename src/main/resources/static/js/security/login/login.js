'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('login', {
                parent: 'account',
                url: '/login',
                data: {
                    authorities: [],
                    pageTitle: 'Login'
                },
                views: {
                    'content@': {
                        templateUrl: '/js/security/login/login.html',
                        controller: 'LoginController'
                    }
                }
            }).state('roles', {
                parent: 'account',
                url: '/roles',
                data: {
                    authorities: [],
                    pageTitle: 'Login'
                },
                views: {
                    'content@': {
                        templateUrl: '/views/roles.html',
                        controller: 'rolesController'
                    }
                }
            }).state('users', {
                parent: 'account',
                url: '/users',
                data: {
                    authorities: [],
                    pageTitle: 'Login'
                },
                views: {
                    'content@': {
                        templateUrl: '/views/users.html',
                        controller: 'usersController'
                    }
                }
            });
    });

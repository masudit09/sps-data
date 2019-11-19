'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('chapters', {
                parent: 'account',
                url: '/chapters',
                data: {
                    authorities: [],
                    pageTitle: 'register.title'
                },
                views: {
                    'content@': {
                        templateUrl: '/views/chapters.html',
                        controller: 'ChapterController'
                    }
                }/*,
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('register');
                        return $translate.refresh();
                    }]
                }*/
            }).state('add-chapter', {
            parent: 'account',
            url: '/add-chapters',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: '/views/chapter-add.html',
                    controller: 'ChapterAddController'
                }
            }
        });
    });

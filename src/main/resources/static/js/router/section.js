'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sections', {
                parent: 'account',
                url: '/sections',
                data: {
                    authorities: [],
                    pageTitle: 'register.title'
                },
                views: {
                    'content@': {
                        templateUrl: '/views/section/list.html',
                        controller: 'ChapterController'
                    }
                }
            }).state('add-section', {
            parent: 'account',
            url: '/add-section',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: '/views/section/add.html',
                    controller: 'ChapterAddController'
                }
            }
        });
    });

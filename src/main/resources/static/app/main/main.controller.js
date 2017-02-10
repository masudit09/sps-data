'use strict';

angular.module('app')
    .controller('MainController', function ($scope, $stateParams , $state, Principal) {
        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });



        $scope.time = 0;
        $scope.total = 0;
        $scope.page = 0;
        $scope.per_page = 20;
        $scope.jobs = [];
        $scope.cats = [];



    });

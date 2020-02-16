angular.module('app').controller('SectionController', function($scope,Section, PaginationSection) {

    $scope.sections=[];
    $scope.currentPage = 0;
    $scope.numPerPage = 0;
    $scope.maxSize = 0;
    $scope.totalElement = 0;
    $scope.itemsPerPage = 20;
    Section.get(function (data) {
        console.log(data)
        $scope.sections = data.content;
        $scope.currentPage = data.number+1;
        $scope.numPerPage = data.size;
        $scope.maxSize = 20;
        $scope.currentPage = data.currentIndex;
        $scope.totalElement = data.totalElements;

    })
    $scope.pageChanged = function() {
        PaginationSection.get({page_num:$scope.currentPage},function (data) {
            console.log(data);
            $scope.sections = data.content;
        })
    };
}).controller('SectionAddController', function($scope,$stateParams,$state, Section, AllChapter) {
    $scope.object={};
    $scope.chapters={};

    if($stateParams.id) {
        Section.get({id:$stateParams.id},function (data) {
            $scope.object = data;
        })
    }

    AllChapter.get(function (data) {
        $scope.chapters = data;
    })
    if($stateParams.id) {
        Section.get({id:$stateParams.id},function (data) {
            $scope.section = data;
        })
    }
    var onSaveSuccess = function (data) {
        $scope.globalMessage = "Chapter Added Successfully";
        $state.go('sections',null, {reload:true})
    };

    var onSaveError = function (result) {
        console.log(result);
        $state.formErrors = "Error Saving Chapter";
    };
    $scope.save = function() {
        console.log($scope.object)
        Section.save($scope.object, onSaveSuccess, onSaveError);
    };
});


angular.module('app').controller('SectionController', function($scope,Section) {

    $scope.sections=[];
    $scope.currentPage = 0;
    $scope.numPerPage = 0;
    $scope.maxSize = 0;
    $scope.totalElement = 0;
    Section.get(function (data) {
        console.log(data)
        $scope.sections = data.content;
        $scope.numPerPage = data.size;
        $scope.maxSize = 20;
        $scope.currentPage = data.currentIndex;
        $scope.totalElement = data.totalElements;

    })

}).controller('SectionAddController', function($scope,$stateParams,$state, Section) {
    $scope.object={};

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


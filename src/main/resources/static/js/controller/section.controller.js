angular.module('app').controller('ChapterController', function($scope,Section) {

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

}).controller('SectionAddController', function($scope,$stateParams,$state, Chapter) {
    $scope.section={};

    if($stateParams.id) {
        Section.get({id:$stateParams.id},function (data) {
            $scope.section = data;
        })
    }
    var onSaveSuccess = function (data) {
        $scope.globalMessage = "Chapter Added Successfully";
        $state.go('chapters',null, {reload:true})
    };

    var onSaveError = function (result) {
        console.log(result);
        $state.formErrors = "Error Saving Chapter";
    };
    $scope.save = function() {
        console.log($scope.itemType)
        Section.save($scope.section, onSaveSuccess, onSaveError);
    };
});


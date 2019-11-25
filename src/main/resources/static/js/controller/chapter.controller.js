angular.module('app').controller('ChapterController', function($scope,Chapter) {

    $scope.chapters=[];
    $scope.currentPage = 0;
    $scope.numPerPage = 0;
    $scope.maxSize = 0;
    $scope.totalElement = 0;
    Chapter.get(function (data) {
        console.log(data)
        $scope.chapters = data.content;
        $scope.numPerPage = data.size;
        $scope.maxSize = 20;
        $scope.currentPage = data.currentIndex;
        $scope.totalElement = data.totalElements;

    })

}).controller('ChapterAddController', function($scope,$stateParams,$state, Chapter) {
    $scope.chapter={};

    if($stateParams.id) {
        Chapter.get({id:$stateParams.id},function (data) {
            $scope.chapter = data;
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
        Chapter.save($scope.chapter, onSaveSuccess, onSaveError);
    };
}).controller('PreviewBookController', function($scope,$stateParams,$state, AllChapter) {
    $scope.chapters = [];
    AllChapter.get(function (data) {
        $scope.chapters = data;
    })
});


angular.module('app').controller('ContentController', function($scope,Content, PaginationContent) {

    $scope.contents=[];
    $scope.currentPage = 0;
    $scope.numPerPage = 0;
    $scope.maxSize = 0;
    $scope.totalElement = 0;
    $scope.itemsPerPage = 20;
    Content.get(function (data) {
        $scope.contents = data.content;
        $scope.currentPage = data.number+1;
        $scope.numPerPage = data.size;
        $scope.maxSize = 20;
        $scope.currentPage = data.currentIndex;
        $scope.totalElement = data.totalElements;

    })
    $scope.pageChanged = function() {
        PaginationContent.get({page_num:$scope.currentPage},function (data) {
            console.log(data);
            $scope.contents = data.content;
        })
    };

}).controller('ContentAddController', function($scope,$stateParams,$state, Content, AllSection) {
    $scope.objects=[{section:{}}];
    $scope.section=null;
    $scope.sections={};

    AllSection.get(function (data) {
        $scope.sections = data;
    })

    var onSaveSuccess = function (data) {
        $scope.globalMessage = "Chapter Added Successfully";
        $state.go('contents',null, {reload:true})
    };

    var onSaveError = function (result) {
        console.log(result);
        $state.formErrors = "Error Saving Chapter";
    };
    $scope.save = function() {
        console.log($scope.objects)
        angular.forEach($scope.objects, function(object) {
            object.section.id = $scope.section.id;
        });
        Content.save($scope.objects, onSaveSuccess, onSaveError);
    };
    $scope.addMoreContent = function() {
        $scope.objects.push({section:{}});
    };
    $scope.removeContent = function($index) {
        $scope.objects.splice($index, 1);
    };
});


angular.module('app').controller('UserController', function($scope,User) {

    $scope.users=[];
    $scope.currentPage = 0;
    $scope.numPerPage = 0;
    $scope.maxSize = 0;
    $scope.totalElement = 0;
    User.get(function (data) {
        $scope.users=data.users.content;
        $scope.numPerPage = data.users.size;
        $scope.maxSize = 10;
        $scope.currentPage = data.currentIndex;
        $scope.totalElement = data.users.totalElements;
    });


    $scope.makeTodos = function() {
        $scope.todos = [];
        for (i=1;i<=11;i++) {
            $scope.todos.push({ text:'todo '+i, done:false});
        }
    };
    $scope.makeTodos();

    $scope.$watch('currentPage + numPerPage', function() {
        var begin = (($scope.currentPage - 1) * $scope.numPerPage)
            , end = begin + $scope.numPerPage;

        $scope.filteredTodos = $scope.todos.slice(begin, end);
    });
});


var app = angular.module('app', []);
app.controller('ctrl', ['$scope', '$http', function ($scope, $http) {
    $scope.interchange_fee_label = '';
    $scope.change_fee_label = function (label) {
        $scope.interchange_fee_label = label;
    }
}]);
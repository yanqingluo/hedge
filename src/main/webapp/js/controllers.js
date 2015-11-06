var assetapp = angular.module('assetapp', []);
assetapp.controller('assetctrl', ['$scope', '$http', function ($scope, $http) {
    $http.get('./s/shares/7').success(function (data) {
        $scope.assetdatas = data.list;
    });

    $scope.currentTab = 'month';
    $scope.getRateClass = function (n) {
        if (n > 0) {
            return "red";
        } else if (n < 0) {
            return "green";
        } else {
            return "black";
        }
    }
    $scope.loaddata = function (days) {
        if (days == 7) {
            $scope.currentTab = "week";
        } else {
            $scope.currentTab = "month";
        }
        $http.get('./s/shares/' + days).success(function (data) {
            $scope.assetdatas = data.list;
        });

    }

    $scope.setCurrentTabClass = function (p) {
        if ($scope.currentTab == p) {
            return "active";
        } else {
            return "";
        }
    }
}]);
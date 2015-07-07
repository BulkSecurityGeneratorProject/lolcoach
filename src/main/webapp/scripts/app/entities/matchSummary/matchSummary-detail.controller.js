'use strict';

angular.module('lolcoachApp')
    .controller('MatchSummaryDetailController', function ($scope, $stateParams, MatchSummary) {
        $scope.matchSummary = {};
        $scope.load = function (id) {
            MatchSummary.get({id: id}, function(result) {
              $scope.matchSummary = result;
            });
        };
        $scope.load($stateParams.id);
    });

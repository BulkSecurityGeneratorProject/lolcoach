'use strict';

angular.module('lolcoachApp')
    .controller('MatchHistoryDetailController', function ($scope, $stateParams, MatchHistory, Summoner) {
        $scope.matchHistory = {};
        $scope.load = function (id) {
            MatchHistory.get({id: id}, function(result) {
              $scope.matchHistory = result;
            });
        };
        $scope.load($stateParams.id);
    });

'use strict';

angular.module('lolcoachApp')
    .controller('SummonerDetailController', function ($scope, $stateParams, Summoner, MatchHistory) {
        $scope.summoner = {};
        $scope.load = function (id) {
            Summoner.get({id: id}, function(result) {
              $scope.summoner = result;
            });
        };
        $scope.load($stateParams.id);
    });

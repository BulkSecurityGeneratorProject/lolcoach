'use strict';

angular.module('lolcoachApp')
    .controller('SummonerController', function ($scope, Summoner, MatchHistory, ParseLinks) {
        $scope.summoners = [];
        $scope.matchhistorys = MatchHistory.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Summoner.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.summoners.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.summoners = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Summoner.get({id: id}, function(result) {
                $scope.summoner = result;
                $('#saveSummonerModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.summoner.id != null) {
                Summoner.update($scope.summoner,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Summoner.save($scope.summoner,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Summoner.get({id: id}, function(result) {
                $scope.summoner = result;
                $('#deleteSummonerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Summoner.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteSummonerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveSummonerModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.summoner = {name: null, region: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

'use strict';

angular.module('lolcoachApp')
    .controller('MatchHistoryController', function ($scope, MatchHistory, Summoner) {
        $scope.matchHistorys = [];
        $scope.summoners = Summoner.query();
        $scope.loadAll = function() {
            MatchHistory.query(function(result) {
               $scope.matchHistorys = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            MatchHistory.get({id: id}, function(result) {
                $scope.matchHistory = result;
                $('#saveMatchHistoryModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.matchHistory.id != null) {
                MatchHistory.update($scope.matchHistory,
                    function () {
                        $scope.refresh();
                    });
            } else {
                MatchHistory.save($scope.matchHistory,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            MatchHistory.get({id: id}, function(result) {
                $scope.matchHistory = result;
                $('#deleteMatchHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MatchHistory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMatchHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMatchHistoryModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.matchHistory = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

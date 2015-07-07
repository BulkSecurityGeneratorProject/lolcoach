'use strict';

angular.module('lolcoachApp')
    .controller('MatchSummaryController', function ($scope, MatchSummary) {
        $scope.matchSummarys = [];
        $scope.loadAll = function() {
            MatchSummary.query(function(result) {
               $scope.matchSummarys = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            MatchSummary.get({id: id}, function(result) {
                $scope.matchSummary = result;
                $('#saveMatchSummaryModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.matchSummary.id != null) {
                MatchSummary.update($scope.matchSummary,
                    function () {
                        $scope.refresh();
                    });
            } else {
                MatchSummary.save($scope.matchSummary,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            MatchSummary.get({id: id}, function(result) {
                $scope.matchSummary = result;
                $('#deleteMatchSummaryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MatchSummary.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMatchSummaryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMatchSummaryModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.matchSummary = {matchCreation: null, mapId: null, matchDuration: null, matchId: null, matchMode: null, matchType: null, matchVersion: null, platformId: null, region: null, season: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });

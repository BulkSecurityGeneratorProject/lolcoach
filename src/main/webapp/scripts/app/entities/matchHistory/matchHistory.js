'use strict';

angular.module('lolcoachApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('matchHistory', {
                parent: 'entity',
                url: '/matchHistory',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'lolcoachApp.matchHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/matchHistory/matchHistorys.html',
                        controller: 'MatchHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('matchHistory');
                        return $translate.refresh();
                    }]
                }
            })
            .state('matchHistoryDetail', {
                parent: 'entity',
                url: '/matchHistory/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'lolcoachApp.matchHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/matchHistory/matchHistory-detail.html',
                        controller: 'MatchHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('matchHistory');
                        return $translate.refresh();
                    }]
                }
            });
    });

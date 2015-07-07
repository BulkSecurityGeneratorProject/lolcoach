'use strict';

angular.module('lolcoachApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('matchSummary', {
                parent: 'entity',
                url: '/matchSummary',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'lolcoachApp.matchSummary.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/matchSummary/matchSummarys.html',
                        controller: 'MatchSummaryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('matchSummary');
                        return $translate.refresh();
                    }]
                }
            })
            .state('matchSummaryDetail', {
                parent: 'entity',
                url: '/matchSummary/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'lolcoachApp.matchSummary.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/matchSummary/matchSummary-detail.html',
                        controller: 'MatchSummaryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('matchSummary');
                        return $translate.refresh();
                    }]
                }
            });
    });

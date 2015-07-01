'use strict';

angular.module('lolcoachApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('summoner', {
                parent: 'entity',
                url: '/summoner',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'lolcoachApp.summoner.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/summoner/summoners.html',
                        controller: 'SummonerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('summoner');
                        return $translate.refresh();
                    }]
                }
            })
            .state('summonerDetail', {
                parent: 'entity',
                url: '/summoner/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'lolcoachApp.summoner.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/summoner/summoner-detail.html',
                        controller: 'SummonerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('summoner');
                        return $translate.refresh();
                    }]
                }
            });
    });

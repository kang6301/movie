
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import PaymentManager from "./components/listers/PaymentCards"
import PaymentDetail from "./components/listers/PaymentDetail"

import MovieManager from "./components/listers/MovieCards"
import MovieDetail from "./components/listers/MovieDetail"

import ReservationManager from "./components/listers/ReservationCards"
import ReservationDetail from "./components/listers/ReservationDetail"

import MessageManager from "./components/listers/MessageCards"
import MessageDetail from "./components/listers/MessageDetail"

import ReviewManager from "./components/listers/ReviewCards"
import ReviewDetail from "./components/listers/ReviewDetail"


import MoviewiewsView from "./components/MoviewiewsView"
import MoviewiewsViewDetail from "./components/MoviewiewsViewDetail"

export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/payments',
                name: 'PaymentManager',
                component: PaymentManager
            },
            {
                path: '/payments/:id',
                name: 'PaymentDetail',
                component: PaymentDetail
            },

            {
                path: '/movies',
                name: 'MovieManager',
                component: MovieManager
            },
            {
                path: '/movies/:id',
                name: 'MovieDetail',
                component: MovieDetail
            },

            {
                path: '/reservations',
                name: 'ReservationManager',
                component: ReservationManager
            },
            {
                path: '/reservations/:id',
                name: 'ReservationDetail',
                component: ReservationDetail
            },

            {
                path: '/messages',
                name: 'MessageManager',
                component: MessageManager
            },
            {
                path: '/messages/:id',
                name: 'MessageDetail',
                component: MessageDetail
            },

            {
                path: '/reviews',
                name: 'ReviewManager',
                component: ReviewManager
            },
            {
                path: '/reviews/:id',
                name: 'ReviewDetail',
                component: ReviewDetail
            },


            {
                path: '/moviewiews',
                name: 'MoviewiewsView',
                component: MoviewiewsView
            },
            {
                path: '/moviewiews/:id',
                name: 'MoviewiewsViewDetail',
                component: MoviewiewsViewDetail
            },


    ]
})

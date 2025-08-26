import http from 'k6/http';

export let options = {
    scenarios: {
        // tps_100: {
        //     executor: 'constant-arrival-rate',
        //     rate: 100, // TPS 100
        //     timeUnit: '1s',
        //     duration: '1m',
        //     preAllocatedVUs: 50,
        //     maxVUs: 200,
        // },
        // tps_1000: {
        //     executor: 'constant-arrival-rate',
        //     rate: 1000, // TPS 1000
        //     timeUnit: '1s',
        //     duration: '1m',
        //     preAllocatedVUs: 500,
        //     maxVUs: 2000,
        // },
        tps_2000: {
            executor: 'constant-arrival-rate',
            rate: 2000, // TPS 2000
            timeUnit: '1s',
            duration: '1m',
            preAllocatedVUs: 1000,
            maxVUs: 4000,
        },
    },
};

export default function () {
    const url = 'http://app:8080/api/transactions';

    const userId = Math.floor(Math.random() * 10) + 1;

    const payload = JSON.stringify({
        userId: userId,
        amount: (Math.random() * 1000).toFixed(2),
        type: "CREATE_PAYMENT"
    });

    const params = {
        headers: { 'Content-Type': 'application/json' },
    };

    http.post(url, payload, params);
}
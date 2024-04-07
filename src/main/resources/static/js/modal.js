async function fetchTest() {
    let modalTest = fetch("/modalTest", {
            method: "POST",
            headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
            body: JSON.stringify({
            title: "Test",
            body: "I am testing!",
            userId: 1,
        }),
        }).then((response) => console.log(response));
    }

fetchTest();

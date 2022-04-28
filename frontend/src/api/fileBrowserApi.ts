export const getFolderContent = (path: string) =>
    fetch(
        "http://localhost:8080/folder/content",
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({path})
        }
    ).then((resp) => resp.json());

export const unzipArchive = (path: string) =>
    fetch(
        "http://localhost:8080/archive/unzip",
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({path})
        }
    ).then((resp) => resp.json());


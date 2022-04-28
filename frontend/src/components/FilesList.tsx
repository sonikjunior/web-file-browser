import React, {useState} from 'react';
import '../App.css';
import {FileDto, Props} from '../dto/FileDto';
import File from "./File";

const FilesList = (props: Props) => {
    const [files, setFiles] = useState(props.files);

    const setListState = (newFile: FileDto) => {
        setFiles([...files, newFile])
    }

    return (
            <div>
                <ul className="files-list">
                    {files.map((file: FileDto) =>
                        (<File
                            file={file}
                            setListState={setListState}
                        />)
                    )}
                </ul>
            </div>
    );
};

export default FilesList;

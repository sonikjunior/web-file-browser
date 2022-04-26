import React from 'react';
import {FileDto, Props} from '../dto/FileDto'
import File from "./File";

const FilesList = (props: Props) => {
    return (
        <div>
            <ul>
                {props.files.map((file: FileDto) =>
                    (<File
                        path={file.path}
                        name={file.name}
                        type={file.type}
                    />)
                )}
            </ul>
        </div>
    );
};

export default FilesList;

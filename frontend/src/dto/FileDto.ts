export interface Props {
    files: FileDto[]
}

export interface FileDto {
    path: string
    name: string
    type: string
}

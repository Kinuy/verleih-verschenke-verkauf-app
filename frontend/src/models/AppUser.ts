export type AppUser = {
    id: string,
    username: string,
    role: "ADMIN" | "USER",
    items: Array<string>
}
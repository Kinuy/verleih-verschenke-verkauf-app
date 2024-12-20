import {ItemCategory} from "./ItemCategory.ts";
import {ItemStatus} from "./ItemStatus.ts";

export type Item = {
    id: string,
    name: string,
    img: string,
    description: string,
    category: ItemCategory,
    status: ItemStatus,
    geocode: [number,number],
    owner: string
}
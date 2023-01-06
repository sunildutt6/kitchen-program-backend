export class Product {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public price: number,
    public imageUrl: string,
    public active: boolean
  ) {}
}
